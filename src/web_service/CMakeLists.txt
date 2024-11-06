add_library(web_service STATIC
    announce_room_json.cpp
    announce_room_json.h
    precompiled_headers.h
    verify_login.cpp
    verify_login.h
    verify_user_jwt.cpp
    verify_user_jwt.h
    web_backend.cpp
    web_backend.h
)

if (MSVC AND ENABLE_LTO)
  target_compile_options(web_service PRIVATE
    /wd5049 # 'string': Embedding a full path may result in machine-dependent output (breaks LTO on MSVC)
  )
endif()

create_target_directory_groups(web_service)

target_compile_definitions(web_service PUBLIC -DENABLE_WEB_SERVICE)
target_link_libraries(web_service PRIVATE borked3ds_common network json-headers httplib cpp-jwt)
target_link_libraries(web_service PUBLIC ${OPENSSL_LIBS})
if(WIN32)
    target_link_libraries(web_service PRIVATE crypt32)
endif()

if (BORKED3DS_USE_PRECOMPILED_HEADERS)
    target_precompile_headers(web_service PRIVATE precompiled_headers.h)
endif()
