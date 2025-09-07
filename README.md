# hkimjp/carmine

A redis client that owes mainly `taoensso.carmine`.

## Usage

start connection to redis server:

    (redis-sever "redis://redis-server:port")

default value will be provided REDIS env var.

    REDIS = "redis://localhost:6379"

## Example

    (require '[hkimjp.carmine :as c])
    (c/redis-server)
    (c/ping)
    (c/set "x" 1)
    (c/get "x")
    (c/setex "now" 10 (java.util.Date.))
    (c/get "now")
    (c/get "now")

## License

Copyright © 2025 Hkim

Distributed under the Eclipse Public License version 1.0.
