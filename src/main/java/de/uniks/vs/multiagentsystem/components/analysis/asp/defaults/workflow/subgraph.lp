#external start(A): fluent(inertial, A).
#external end(A): fluent(inertial, A).
#external start(A): _n_fluent(inertial, A).
#external end(A): _n_fluent(inertial, A).

node(X) :- start(X), fluent(inertial, X).
node(X) :- end(X), fluent(inertial, X).
node(X) :- path(S, X), start(S), fluent(inertial, X).
node(X) :- end(E), path(X, E), fluent(inertial, X).

_n_node(X) :- start(X), _n_fluent(inertial, X).
_n_node(X) :- end(X), _n_fluent(inertial, X).
_n_node(X) :- path(S, X), start(S), _n_fluent(inertial, X).
_n_node(X) :- end(E), path(X, E), _n_fluent(inertial, X).

originalPath(A, B) :- edge(A, B).
originalPath(A, C) :- originalPath(A, B), originalPath(B, C).

path(S, A) :- start(S), end(E), originalPath(S, A), not originalPath(E, A).
path(A, E) :- start(S), end(E), originalPath(A, E), not originalPath(A, S).
path(A, B) :- node(A), node(B), start(S), end(E), path(S,A), path(B,E),
                originalPath(A, B).

#show path/2.
#show node/1.

