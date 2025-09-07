# hkimjp/carmine-farm

A redis client that owes mainly `taoensso.carmine`.

## Usage

start connection to redis server:

    (redis-sever "redis://redis-server:port")

server uri can be provided as env var.

    REDIS = "redis://localhost:6379"

## Example

    > clj
    user=> (require '[hkimjp.carmine :as c])
    nil

    user=> (c/redis-server)

    user=> (c/ping)
    "PONG"

    user=> (c/set "x" 1)
    "OK"

    user=> (c/get "x")
    "1"

    user=> (c/setex "now" 10 (java.util.Date.))
    "OK"

    user=> (c/get "now")
    #inst "2025-09-07T02:49:20.226-00:00"

    user=> (c/get "now")
    nil

## License

Copyright © 2025 Hkim

Distributed under the Eclipse Public License version 1.0.
