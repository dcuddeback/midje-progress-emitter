(ns midje-progress-emitter.core
  (:require [midje-progress-emitter.util :as util]
            [midje-progress-emitter.color :as color]
            [midje-progress-emitter.namespace :as namespace]
            [midje-progress-emitter.anomalies :as anomalies]
            [midje.data.fact :as fact]
            [midje.emission.plugins.default :as default]
            [midje.emission.plugins.silence :as silence]
            [midje.emission.state :as state]))


(defn emit-anomalies! []
  (util/emit-newline!)
  (let [anomalies (anomalies/anomalies)]
    (when-not (empty? anomalies)
      (util/emit-lines
        (anomalies/summarize-anomalies anomalies))
      (util/emitln)))
  (anomalies/forget-anomalies!))


(defn pass []
  (util/emit (color/pass ".")))

(defn fail [failure-map]
  (anomalies/record-anomaly! (anomalies/failure failure-map))
  (util/emit (color/fail "F")))

(defn future-fact [description position]
  (anomalies/record-anomaly! (anomalies/pending description position))
  (util/emit (color/warn "*")))


(defn possible-new-namespace [name]
  (when (namespace/new-namespace? name)
    (emit-anomalies!)
    (util/emitln (color/note (format "= %s" name)))
    (namespace/set-namespace! name)))


(defn starting-to-check-top-level-fact [fact]
  (util/emit (format "  %s " (fact/name fact))))

(defn finishing-top-level-fact [fact]
  (emit-anomalies!))


(defn starting-fact-stream []
  (namespace/forget-namespace!)
  (anomalies/forget-anomalies!))

(defn finishing-fact-stream [& args]
  (emit-anomalies!)
  (apply default/finishing-fact-stream args))


(defn make-map [& keys]
  (zipmap keys
          (map #(ns-resolve *ns* (symbol (name %))) keys)))


(state/install-emission-map (merge silence/emission-map
                                   (make-map :pass
                                             :fail
                                             :future-fact
                                             :possible-new-namespace
                                             :starting-to-check-top-level-fact
                                             :finishing-top-level-fact
                                             :starting-fact-stream
                                             :finishing-fact-stream)))
