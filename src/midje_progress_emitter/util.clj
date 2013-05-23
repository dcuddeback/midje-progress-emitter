(ns midje-progress-emitter.util
  (:require [midje.emission.plugins.util :as util]
            [clojure.test :as ct]))

(def needs-newline (atom false))

(defn needs-newline? []
  @needs-newline)

(defn needs-newline! []
  (reset! needs-newline true))

(defn reset-newline! []
  (reset! needs-newline false))


(defn emit [& strings]
  (ct/with-test-out
    (apply print strings)
    (flush))
  (needs-newline!))

(defn emitln [& strings]
  (ct/with-test-out
    (apply println strings)))

(def emit-lines util/emit-lines)

(defn emit-newline! []
  (when (needs-newline?)
    (emitln)
    (reset-newline!)))
