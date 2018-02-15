remove(F) :- node(F), not _n_node(F).
add(F) :- not node(F), _n_node(F).
remove(A) :- action(A), not _n_action(A).
add(A) :- not action(A), _n_action(A).

addEdge(A, B) :- not edge(A, B), _n_edge(A, B).
removeEdge(A, B) :- edge(A, B), not _n_edge(A, B).

unchanged(F) :- not add(F), not remove(F), node(F).
unchanged(A) :- not add(A), not remove(A), action(A).

replace(A, B) :- remove(A), add(B), action(A), _n_action(B),
                occurs(A,X), _n_occurs(B,X), step(X),
                action(C), occurs(C, X-1), _n_occurs(C, X-1), A != C, B != C,
                action(D), occurs(D, X+1), _n_occurs(D, X+1), A != D, B != D.

branch(A, B, C, D) :- edge(A, C), edge(D, B), path(C, D),
                unchanged(A), unchanged(B), add(C), add(D).

branch(A, B, C, D) :- _n_edge(A, C), _n_edge(D, B), path(C, D),
                unchanged(A), unchanged(B), add(C), add(D).

branch(A, B, C, C) :- edge(A, C), edge(C, B),
                unchanged(A), unchanged(B), add(C).

path(A, B) :- edge(A, B).
path(A, C) :- path(A, B), path(B, C).

move(A) :- addEdge(B, A), addEdge(A, C),
           unchanged(A), unchanged(B), unchanged(C).

#show start/1.
#show end/1.
#show remove/1.
#show add/1.
#show unchanged/1.
#show addEdge/2.
#show removeEdge/2.
#show path/2.
#show branch/4.
#show move/1.
#show replace/2.