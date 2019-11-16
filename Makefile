default: Oxo

%: %.java
	javac $@.java
	java -ea $@
