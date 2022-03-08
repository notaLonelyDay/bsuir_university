cmake_minimum_required(VERSION 3.20)
set(CMAKE_BUILD_TYPE Debug)
project(ClionProject)

set(CMAKE_CXX_STANDARD 14)


add_executable(tcp_server tcp/server.cpp)
add_executable(tcp_client tcp/client.cpp)

add_executable(udp_server udp/server.cpp)
add_executable(udp_client udp/client.cpp)

target_link_libraries(tcp_server wsock32 ws2_32)
target_link_libraries(tcp_client wsock32 ws2_32)

target_link_libraries(udp_server wsock32 ws2_32)
target_link_libraries(udp_client wsock32 ws2_32)
