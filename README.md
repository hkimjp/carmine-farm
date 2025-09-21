# hkimjp/carmine-farm

My learning place about using `taoensso.carmine`.

## Installation

deps.edn:

```
io.github.hkimjp/carmine-farm {:git/tag "0.2.8" :git/sha "f06c59e"}
```

## Usage


start connection to redis server:

    (create-conn "redis://redis-server:port")

server uri can be provided as an env-var.

    REDIS = "redis://localhost:6379"

## Example

    > clj
    user=> (require '[hkimjp.carmine :as c])
    nil

    user=> (c/create-conn)

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

    user=> (c/close-conn) ; no effect 0.2.6.


## License

Copyright © 2025 Hkim

Distributed under the Eclipse Public License version 1.0.
