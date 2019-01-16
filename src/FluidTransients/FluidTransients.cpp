
// A tool from the FluCoMa project, funded by the European Research Council (ERC) under the European Union’s Horizon 2020 research and innovation programme (grant agreement No 725899)


#include <clients/rt/TransientClient.hpp>
#include <SC_PlugIn.hpp>

#include <FluidSCWrapper.hpp>

static InterfaceTable *ft;

PluginLoad(FluidSTFTUGen) {
  ft = inTable;
//  registerUnit<fluid::wrapper::FluidTransients>(ft, "FluidTransients");
  using namespace fluid::client;
  makeSCWrapper<TransientClient<double,float>>(ft, "FluidTransients",TransientParams);
}
