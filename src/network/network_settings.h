// Copyright 2020 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

#pragma once

#include <string>

namespace NetSettings {

struct Values {
    // WebService
    std::string web_api_url;
    std::string borked3ds_username;
    std::string borked3ds_token;
} extern values;

} // namespace NetSettings
