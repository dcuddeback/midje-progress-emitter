(ns midje-progress-emitter.anomalies
  (:require [midje-progress-emitter.color :as color]
            [midje.emission.plugins.default-failure-lines :as failure-lines]
            [clojure.string :as string]))


(defn failure [failure-map]
  {:type :failure
   :value failure-map})

(defn pending [description position]
  (let [[file line] position]
    {:type :pending
     :description description
     :position {:file file
                :line line}}))


(defmulti summarize :type)

(defmethod summarize :failure [{:keys [value]}]
  (failure-lines/summarize value))

(defmethod summarize :pending [{:keys [description position]}]
  ["" (format "%s %s at (%s:%d)"
              (color/warn "PENDING")
              (string/join " " description)
              (:file position)
              (:line position))])

(defn summarize-anomalies [anomalies]
  (mapcat summarize anomalies))


(def ^:private anomalies-atom (atom []))

(defn record-anomaly! [anomaly]
  (swap! anomalies-atom conj anomaly))

(defn forget-anomalies! []
  (reset! anomalies-atom []))

(defn anomalies []
  @anomalies-atom)
