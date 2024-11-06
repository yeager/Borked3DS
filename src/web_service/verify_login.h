// Copyright 2017 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

#pragma once

#include <string>

namespace WebService {

/**
 * Checks if username and token is valid
 * @param host the web API URL
 * @param username Borked3DS username to use for authentication.
 * @param token Borked3DS token to use for authentication.
 * @returns a bool indicating whether the verification succeeded
 */
bool VerifyLogin(const std::string& host, const std::string& username, const std::string& token);

} // namespace WebService
