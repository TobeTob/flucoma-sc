
// A tool from the FluCoMa project, funded by the European Research Council (ERC) under the European Union’s Horizon 2020 research and innovation programme (grant agreement No 725899)

#include <clients/nrt/DatasetClient.hpp>
#include <clients/nrt/CorpusClient.hpp>
#include <FluidSCWrapper.hpp>

static InterfaceTable *ft;

PluginLoad(FluidSTFTUGen)
{
  ft = inTable;
  using namespace fluid::client;
  makeSCWrapper<NRTThreadedDataset>("FluidDataset", ft);
  makeSCWrapper<NRTThreadedCorpus>("FluidCorpus", ft);
}
