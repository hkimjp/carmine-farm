set dotenv-load

help:
  just --list

plus:
  clj -X:dev:plus

nrepl:
  clj -M:dev:nrepl

kaocha:
  clojure -M:dev -m kaocha.runner

update:
  clojure -Tantq outdated :upgrade true :force true

clean:
  rm -rf target
