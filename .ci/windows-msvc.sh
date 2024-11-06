#!/bin/sh -ex

mkdir build && cd build

cmake .. -G Ninja \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_C_COMPILER_LAUNCHER=ccache \
    -DCMAKE_CXX_COMPILER_LAUNCHER=ccache \
    -DENABLE_LTO=ON \
    -DENABLE_QT_TRANSLATION=ON \
    -DUSE_SYSTEM_GLSLANG=OFF \
    -DUSE_SYSTEM_VULKAN_HEADERS=OFF \
    -DUSE_SYSTEM_VMA=OFF \
    -DBORKED3DS_USE_EXTERNAL_VULKAN_SPIRV_TOOLS=ON \
    -DBORKED3DS_ENABLE_COMPATIBILITY_REPORTING=OFF \
    -DUSE_DISCORD_PRESENCE=ON

ninja
ninja bundle

ccache -s -v

ctest -VV -C Release || echo "::error ::Test error occurred on Windows build"
