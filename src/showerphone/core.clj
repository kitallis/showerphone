(ns showerphone.core
  (:require [overtone.live :refer :all]))

(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(defn note->hz [music-note]
  (midi->hz (note music-note)))

(defn new-wave [music-note]
  (saw-wave (note->hz music-note)))

(defn play-chord [chord]
  (doseq [note chord] (new-wave note)))

(defonce metro (metronome 120))

(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 6 beat-num)) (play-chord (chord :F3 :sus4)))
  (apply-at (m (+ 8 beat-num)) chord-progression-beat m (+ 8 beat-num) [])
  )

(chord-progression-beat metro (metro))

(stop)
