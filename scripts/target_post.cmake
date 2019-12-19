
target_compile_features(${PLUGIN} PRIVATE cxx_std_14)

if(MSVC)
  target_compile_options(${PLUGIN} PRIVATE /W3)
else()
  target_compile_options(${PLUGIN} PRIVATE 
    -Wall -Wextra -Wpedantic -Wreturn-type -Wconversion -Wno-conversion -Wno-c++11-narrowing -Wno-sign-compare
  )
endif()

set_target_properties(${PLUGIN} PROPERTIES
    CXX_STANDARD 14
    CXX_STANDARD_REQUIRED YES
    CXX_EXTENSIONS NO
)

target_link_libraries(
  ${PLUGIN}
  PRIVATE
  FLUID_DECOMPOSITION
  # FLUID_MANIP
  FLUID_SC_WRAPPER  
  HISSTools_FFT
)

target_include_directories(
  ${PLUGIN}
  PRIVATE
  ${LOCAL_INCLUDES}
)

target_include_directories(
  ${PLUGIN}
  SYSTEM PRIVATE
  "${SC_PATH}/include/plugin_interface"
  "${SC_PATH}/include/common"
  "${SC_PATH}/common"
  "${SC_PATH}/external_libraries/boost" #we need boost::align for deallocating buffer memory :-(
)

get_property(HEADERS TARGET FLUID_DECOMPOSITION PROPERTY INTERFACE_SOURCES)
source_group(TREE "${fluid_decomposition_SOURCE_DIR}/include" FILES ${HEADERS})

# get_property(HEADERS TARGET FLUID_MANIP PROPERTY INTERFACE_SOURCES)
# source_group(TREE "${fluid_manipulation_SOURCE_DIR}/include" FILES ${HEADERS})
# 
# if (SUPERNOVA)
#     target_include_directories(
#       ${PLUGIN}
#       SYSTEM PRIVATE
#       "${SC_PATH}/external_libraries/nova-tt"
#       "${SC_PATH}/external_libraries/boost_lockfree"
#       "${SC_PATH}/external_libraries/boost-lockfree"
#     )
# endif()

if(MINGW)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -mstackrealign")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -mstackrealign")
endif()

if(MSVC)
  target_compile_options(${PLUGIN} PRIVATE /arch:AVX -D_USE_MATH_DEFINES)
else()
  target_compile_options(
     ${PLUGIN} PRIVATE $<$<NOT:$<CONFIG:DEBUG>>: -mavx> -fvisibility=hidden
  )
endif()
