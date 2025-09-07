set dotenv-load

help:
  just --list

nrepl:
  clj -M:dev:nrepl

update:
  clojure -Tantq outdated :upgrade true :force true

clean:
  rm -rf target
