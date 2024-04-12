(ns notes-app.guitar
  (:require [notes-app.chords :as chords]
            [notes-app.scales :as scales]))

(def guitar-tunings
  {:standard [:E :A :D :G :B :E]
   :dadgad [:D :A :D :G :A :D]})

(defn string-number->note [tuning]
  (into {} (reverse (map-indexed (fn [index string]
                                   [(inc index) string]) (reverse tuning)))))

(defn build-guitar-fretboard [tuning]
  (let [string->note (string-number->note tuning)
        string (into {} (map (fn build-frets [[string note]]
                               {string (take 24 (cycle (scales/scale :chromatic note)))}) string->note))]
    string))

(defn get-frets-for-string-for-chord [string-notes chord]
  (let [closest-frets (into [](keep-indexed
                             (fn [index item]
                               (let [note (first item)
                                     open-string (first string-notes)]
                                 (if (contains? (:notes chord) open-string)
                                   {:fret 0 :note note}
                                   (when (contains? (:notes chord) note)
                                     {:fret index :note note})))) string-notes))]
    closest-frets))

(comment
  (let [string-notes (take 24 (cycle (scales/scale :chromatic :D)))
        chord (chords/chord :M7 :G)]
    (get-frets-for-string-for-chord string-notes chord)))

(defn distance [[fret-a & rest-a] [fret-b & rest-b]]
  #dbg(if (or (nil? rest-a) (nil? rest-b))
        (cond
          (= fret-a fret-b) 0
          (and (zero? fret-a) fret-b) 1
          (and fret-a (zero? fret-b)) 1
          (and fret-a fret-b) (abs (- fret-a fret-b)))
        (distance rest-a rest-b)))


(defn get-finger-positions
  "Map of string-number->{:fret number, :note note}"
  [guitar chord]
  (into {} (map (fn [[string-number string-notes]]
                  {string-number (get-frets-for-string-for-chord string-notes chord)}) guitar)))


(defn extract-chord-positions [positions]
  (let [fret-vectors (vals positions)
        num-frets (count (first fret-vectors))]
    (mapv (fn [i]
            (mapv (fn [entry] (:fret (nth entry i))) fret-vectors))
          (range num-frets))))

(comment
  (let [guitar (build-guitar-fretboard (:standard guitar-tunings))
        chord (chords/chord :M :G)
        positions (get-finger-positions guitar chord)
        chords (extract-chord-positions positions)
        distance ()]
    chords)
  (get-frets-for-string-for-chord (scales/scale :chromatic :E) (chords/chord :M :G)))