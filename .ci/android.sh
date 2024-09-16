#!/bin/bash -ex

export NDK_CCACHE=$(which ccache)

if [ ! -z "${ANDROID_KEYSTORE_B64}" ]; then
    export ANDROID_KEYSTORE_FILE="${GITHUB_WORKSPACE}/ks.jks"
    base64 --decode <<< "${ANDROID_KEYSTORE_B64}" > "${ANDROID_KEYSTORE_FILE}"
fi

# Build Vulkan-ValidationLayers
mkdir -p src/android/app/build/tmp
cd externals/Vulkan-ValidationLayers
python3 scripts/android.py --config Release --app-abi 'arm64-v8a x86_64' --app-stl c++_static
cd build-android/libs
zip -r Vulkan-ValidationLayers.zip lib
mv Vulkan-ValidationLayers.zip ../../../../src/android/app/build/tmp
cd ../../../..

# Build Citra
cd src/android
chmod +x ./gradlew
./gradlew assembleRelease
./gradlew bundleRelease

ccache -s -v

if [ ! -z "${ANDROID_KEYSTORE_B64}" ]; then
    rm "${ANDROID_KEYSTORE_FILE}"
fi
