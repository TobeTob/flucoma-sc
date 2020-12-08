FluidBufNMFCross : FluidBufProcessor {
    
    *kr  { |source, target, output , timeSparsity = 10, polyphony = 7, iterations = 50, windowSize = 1024, hopSize = -1, fftSize = -1, trig = 1, blocking = 0|
        
        source = source.asUGenInput;
        target = target.asUGenInput;
        output = output.asUGenInput;
        source.isNil.if {"FluidBufNMFCross:  Invalid source buffer".throw};
        target.isNil.if {"FluidBufNMFCross:  Invalid target buffer".throw};
        output.isNil.if {"FluidBufNMFCross:  Invalid output buffer".throw};
         
		^FluidProxyUgen.kr(\FluidBufNMFCrossTrigger, -1, source, target, output, timeSparsity, polyphony, iterations,  windowSize, hopSize, fftSize, trig, blocking);
	}

    *process { |server, source, target, output , timeSparsity = 10, polyphony = 7, iterations = 50, windowSize = 1024, hopSize = -1, fftSize = -1, freeWhenDone = true, action|
        
        source = source.asUGenInput;
        target = target.asUGenInput;
        output = output.asUGenInput;
        source.isNil.if {"FluidBufNMFCross:  Invalid source buffer".throw};
        target.isNil.if {"FluidBufNMFCross:  Invalid target buffer".throw};
        output.isNil.if {"FluidBufNMFCross:  Invalid output buffer".throw};

        
        ^this.new(
			server, nil, [output]
		).processList(
			[source, target, output, timeSparsity, polyphony, iterations,  windowSize, hopSize, fftSize,0],freeWhenDone, action
		);
	}

    *processBlocking { |server, source, target, output , timeSparsity = 10, polyphony = 7, iterations = 50, windowSize = 1024, hopSize = -1, fftSize = -1, freeWhenDone = true, action|
        
        source = source.asUGenInput;
        target = target.asUGenInput;
        output = output.asUGenInput;
        source.isNil.if {"FluidBufNMFCross:  Invalid source buffer".throw};
        target.isNil.if {"FluidBufNMFCross:  Invalid target buffer".throw};
        output.isNil.if {"FluidBufNMFCross:  Invalid output buffer".throw};

        
        ^this.new(
			server, nil, [output]
		).processList(
			[source, target, output, timeSparsity, polyphony, iterations,  windowSize, hopSize, fftSize,1],freeWhenDone, action
		);
	}
}
