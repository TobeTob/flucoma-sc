FluidBufSpectralShape : UGen {

    var <>synth, <>server;

    *kr{ |source, startFrame = 0, numFrames = -1, startChan = 0, numChans = -1, features,  windowSize = 1024, hopSize = -1, fftSize = -1, doneAction = 0|

		var maxFFTSize = if (fftSize == -1) {windowSize.nextPowerOfTwo} {fftSize};

		source = source.asUGenInput;
		features = features.asUGenInput;

		source.isNil.if {"FluidBufSpectralShape:  Invalid source buffer".throw};
		features.isNil.if {"FluidBufSpectralShape:  Invalid features buffer".throw};

		//NB For wrapped versions of NRT classes, we set the params for maxima to
		//whatever has been passed in language-side (e.g maxFFTSize still exists as a parameter for the server plugin, but makes less sense here: it just needs to be set to a legal value)

		^this.multiNew(\control, source, startFrame, numFrames, startChan, numChans, features, windowSize, hopSize, fftSize, maxFFTSize, doneAction);

	}

    *process { |server, source, startFrame = 0, numFrames = -1, startChan = 0, numChans = -1, features,  windowSize = 1024, hopSize = -1, fftSize = -1, action|

        var synth, instance;
		source = source.asUGenInput;
		features = features.asUGenInput;

		source.isNil.if {"FluidBufSpectralShape:  Invalid source buffer".throw};
		features.isNil.if {"FluidBufSpectralShape:  Invalid features buffer".throw};

		server = server ? Server.default;
        server.ifNotRunning({
            "WARNING: Server not running".postln;
            ^nil;
        });
        synth = { instance = FluidBufSpectralShape.kr(source, startFrame, numFrames, startChan, numChans, features, windowSize, hopSize, fftSize, doneAction:Done.freeSelf)}.play(server);

		//NB For wrapped versions of NRT classes, we set the params for maxima to
		//whatever has been passed in language-side (e.g maxFFTSize still exists as a parameter for the server plugin, but makes less sense here: it just needs to be set to a legal value)
		forkIfNeeded{
            synth.waitForFree;
			server.sync;
			features = server.cachedBufferAt(features); features.updateInfo; server.sync;
			action.value(features);
		};
        instance.synth = synth;
        instance.server = server;
        ^instance;
	}

    cancel{
        if(this.server.notNil)
        {this.server.sendMsg("/u_cmd", this.synth.nodeID, this.synthIndex, "cancel")};
    }
}
