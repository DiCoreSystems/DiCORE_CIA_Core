remove(T) :- type(T, N), not _n_type(T, N).
remove(S) :- service(S), not _n_service(S).
remove(E) :- endpoint(E), not _n_endpoint(E).
remove(O) :- operation(O), not _n_operation(O).
remove(I) :- interface(I), not _n_interface(I).
remove(M) :- messageName(M), not _n_messageName(M).
remove(M) :- messageName(M), not _n_messageName(M).
remove(M) :- messagePart(M, P), not _n_messagePart(M, P), type(P, T).
remove(I) :- not import(I), _n_import(I).

add(T) :- not type(T, N), _n_type(T, N).
add(S) :- not service(S), _n_service(S).
add(E) :- not endpoint(E), _n_endpoint(E).
add(O) :- not operation(O), _n_operation(O).
add(I) :- not interface(I), _n_interface(I).
add(M) :- not messageName(M), _n_messageName(M).
add(M) :- not messagePart(M, P), _n_messagePart(M, P), _n_type(P, T).
add(I) :- not import(I), _n_import(I).


#show remove/1.
#show add/1.
#show rename/2.