% Type Definitions
type(N, T) :- input(N, T).
type(N, T) :- output(N, T).
type(N, T) :- fault(N, T).

_n_type(N, T) :- _n_input(N, T).
_n_type(N, T) :- _n_output(N, T).
_n_type(N, T) :- _n_fault(N, T).

primitive(string).
primitive(boolean).
primitive(decimal).
primitive(float).
primitive(double).
primitive(dateTime).
primitive(duration).
primitive(time).
primitive(date).
primitive(gYearMonth).
primitive(gYear).
primitive(gDay).
primitive(gMonthDay).
primitive(gMonth).
primitive(hexBinary).
primitive(base64Binary).
primitive(anyURI).
primitive(qName).
primitive(nOTATION).

primitive(N, T) :- type(N, T), primitive(T).
_n_primitive(N, T) :- _n_type(N, T), primitive(T).

complex(N, T) :- type(N, T), not primitive(T).
_n_complex(N, T) :- _n_type(N, T), not primitive(T).

contains(X, Z) :- complex(X, Y), complex(Y, Z), X != Y, X != Z.
contains(X, Z) :- complex(X, Y), primitive(Y, Z), X != Y, X != Z.

contains(X, Z) :- _n_complex(X, Y), _n_complex(Y, Z).
contains(X, Z) :- _n_complex(X, Y), _n_primitive(Y, Z).

% Input/Output/Fault Definitions.
input(N, T) :- input(O, N, T), operation(O).
output(N, T) :- output(O, N, T), operation(O).
fault(N, T) :- fault(O, N, T), operation(O).

input(N, T) :- input(I, N, T), interface(I).
output(N, T) :- output(I, N, T), interface(I).
fault(N, T) :- fault(I, N, T), interface(I).

_n_input(N, T) :- _n_input(O, N, T), _n_operation(O).
_n_output(N, T) :- _n_output(O, N, T), _n_operation(O).
_n_fault(N, T) :- _n_fault(O, N, T), _n_operation(O).

_n_input(N, T) :- _n_input(I, N, T), _n_interface(I).
_n_output(N, T) :- _n_output(I, N, T), _n_interface(I).
_n_fault(N, T) :- _n_fault(I, N, T), _n_interface(I).

% Service Definitions
_n_service(N) :- _n_service(N, I, E), _n_interface(I), _n_endpoint(E).
_n_service(N) :- _n_service(N, B), _n_binding(B).

_n_endpoint(N) :- _n_endpoint(N, B, A), _n_binding(B), _n_address(A).

service(N) :- service(N, I, E), interface(I), endpoint(E).
service(N) :- service(N, B), binding(B).

endpoint(N) :- endpoint(N, B, A), binding(B), address(A).

% Interface Definitions
interface(I) :- interface(I, O), operation(O).
interface(N) :- interface(N, F, O), fault(N, F, T), operation(O).

_n_interface(I) :- _n_interface(I, O), _n_operation(O).
_n_interface(N) :- _n_interface(N, F, O), _n_fault(N, F, T), _n_operation(O).

% Message Definitions
messageName(M) :- messagePart(M, P), type(P, T).

_n_messageName(M) :- _n_messagePart(M, P), _n_type(P, T).

% Operation Definitions
operation(O) :- operation(O, I, Out, F), input(O, I, T1), output(O, Out, T2), fault(O, F, T3).
operation(O) :- operation(O, I, Out), input(O, I, T1), output(O, Out, T2).
operation(O) :- operation(O, I, Out), messageName(I), messageName(O).

_n_operation(O) :- _n_operation(O, I, Out, F), _n_input(O, I, T1), _n_output(O, Out, T2), _n_fault(O, F, T3).
_n_operation(O) :- _n_operation(O, I, Out), _n_input(O, I, T1), _n_output(O, Out, T2).
_n_operation(O) :- _n_operation(O, I, Out), _n_messageName(I), _n_messageName(O).