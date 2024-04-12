(ns notes-app.core
  (:require [notes-app.chords :as chords]
            [notes-app.guitar :as guitar]
            [notes-app.renderer :as renderer])
  (:gen-class))

(comment
  (let [tuning (guitar/guitar-tunings :standard)
        guitar (guitar/build-guitar-fretboard tuning)
        chord (chords/chord :M :F)
        finger-positions (guitar/get-finger-positions guitar chord)]
    (println "To play the chord of" (:name chord))
    (println "In the following tuning" (map name tuning))
    (renderer/render finger-positions)))

