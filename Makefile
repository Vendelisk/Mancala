JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) *.java -Xlint

default: .java.class

clean:
	$(RM) *.class
