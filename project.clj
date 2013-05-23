(defproject midje-progress-emitter "0.1.0"
  :description "A Midje emitter that prints a progress bar while facts are checked."
  :url "https://github.com/dcuddeback/midje-progress-emitter"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo" :username :gpg :password :gpg}]
                        ["snapshots" {:url "https://clojars.org/repo" :username :gpg :password :gpg}]]

  :profiles {:provided {:dependencies [[midje "1.5.1"]]}})
