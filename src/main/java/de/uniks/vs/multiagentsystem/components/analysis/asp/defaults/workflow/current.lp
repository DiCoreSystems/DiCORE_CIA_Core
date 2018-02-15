step(0..n).

inertial(poi).
holds(poi,I+1) :- 
           inertial(poi), 
           holds(poi,I),
           not -holds(poi,I+1), step(I).
-holds(poi,I+1) :- 
           inertial(poi), 
           -holds(poi,I),
           not holds(poi,I+1), step(I).

inertial(absolCoord).
holds(absolCoord,I+1) :- 
           inertial(absolCoord), 
           holds(absolCoord,I),
           not -holds(absolCoord,I+1), step(I).
-holds(absolCoord,I+1) :- 
           inertial(absolCoord), 
           -holds(absolCoord,I),
           not holds(absolCoord,I+1), step(I).

inertial(chooseMapType).
holds(chooseMapType,I+1) :- 
           inertial(chooseMapType), 
           holds(chooseMapType,I),
           not -holds(chooseMapType,I+1), step(I).
-holds(chooseMapType,I+1) :- 
           inertial(chooseMapType), 
           -holds(chooseMapType,I),
           not holds(chooseMapType,I+1), step(I).

inertial(geoMap).
holds(geoMap,I+1) :- 
           inertial(geoMap), 
           holds(geoMap,I),
           not -holds(geoMap,I+1), step(I).
-holds(geoMap,I+1) :- 
           inertial(geoMap), 
           -holds(geoMap,I),
           not holds(geoMap,I+1), step(I).

inertial(polMap).
holds(polMap,I+1) :- 
           inertial(polMap), 
           holds(polMap,I),
           not -holds(polMap,I+1), step(I).
-holds(polMap,I+1) :- 
           inertial(polMap), 
           -holds(polMap,I),
           not holds(polMap,I+1), step(I).

inertial(extractRelevantData).
holds(extractRelevantData,I+1) :- 
           inertial(extractRelevantData), 
           holds(extractRelevantData,I),
           not -holds(extractRelevantData,I+1), step(I).
-holds(extractRelevantData,I+1) :- 
           inertial(extractRelevantData), 
           -holds(extractRelevantData,I),
           not holds(extractRelevantData,I+1), step(I).

inertial(showData).
holds(showData,I+1) :- 
           inertial(showData), 
           holds(showData,I),
           not -holds(showData,I+1), step(I).
-holds(showData,I+1) :- 
           inertial(showData), 
           -holds(showData,I),
           not holds(showData,I+1), step(I).

action(dopoi).
-occurs(dopoi,I) :- not occurs(dopoi,I), step(I).

action(doabsolCoord).
-occurs(doabsolCoord,I) :- not occurs(doabsolCoord,I), step(I).

action(dochooseMapType).
-occurs(dochooseMapType,I) :- not occurs(dochooseMapType,I), step(I).

action(dogeoMap).
-occurs(dogeoMap,I) :- not occurs(dogeoMap,I), step(I).

action(dopolMap).
-occurs(dopolMap,I) :- not occurs(dopolMap,I), step(I).

action(doextractRelevantData).
-occurs(doextractRelevantData,I) :- not occurs(doextractRelevantData,I), step(I).

action(doshowData).
-occurs(doshowData,I) :- not occurs(doshowData,I), step(I).

-holds(poi,0).
-holds(absolCoord,0).
-holds(chooseMapType,0).
-holds(geoMap,0).
-holds(polMap,0).
-holds(extractRelevantData,0).
-holds(showData,0).

holds(poi,T+1) :- 
          -holds(poi,T),
           occurs(dopoi,T).

occurs(dopoi,0).

holds(absolCoord,T+1) :- 
           holds(poi,T),
          -holds(absolCoord,T),
           occurs(doabsolCoord,T).

occurs(doabsolCoord,1).

holds(chooseMapType,T+1) :- 
           holds(absolCoord,T),
          -holds(chooseMapType,T),
           occurs(dochooseMapType,T).

occurs(dochooseMapType,2).

holds(geoMap,T+1) :- 
           holds(chooseMapType,T),
          -holds(geoMap,T),
           occurs(dogeoMap,T).

occurs(dogeoMap,3).

holds(polMap,T+1) :- 
           holds(chooseMapType,T),
          -holds(polMap,T),
           occurs(dopolMap,T).

occurs(dopolMap,3).

holds(extractRelevantData,T+1) :- 
           holds(geoMap,T),
          -holds(extractRelevantData,T),
           occurs(doextractRelevantData,T).

occurs(doextractRelevantData,4).

holds(showData,T+1) :- 
           holds(extractRelevantData,T),
          -holds(showData,T),
           occurs(doshowData,T).

occurs(doshowData,5).

