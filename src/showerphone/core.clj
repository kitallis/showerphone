(ns showerphone.core
  (:require [overtone.live :refer :all]
            [overtone.inst.sampled-piano :refer :all]))

(defn play-chord [chord]
  (doseq [note chord] (sampled-piano note)))

(defonce one-twenty-bpm (metronome 120))

(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 6 beat-num)) (play-chord (chord :F3 :sus4)))
  (apply-at (m (+ 8 beat-num)) chord-progression-beat m (+ 8 beat-num) [])
)

(def kick
  (sample (freesound-path 777)))

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-by (nome (inc  beat)) looper nome sound [])))

(looper one-twenty-bpm kick)
(chord-progression-beat one-twenty-bpm (one-twenty-bpm))
(stop)
