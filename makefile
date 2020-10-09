JAVA=java
JAVAC=javac
JFLEX=jflex
JAVACUP=java-cup-11a.jar
CLASSPATH=$(JAVACUP):.

FILE_FLEX=hepial.flex
FILE_CUP=hepial.cup
FILE_JAVA_NAME=HepialLexer
FILE_TEST_PRG_NAME=Hepialc

TEST_CLASS = Hepialc
TESTS = clean all

OUTPUT=demo
ifdef FILEOUT
	OUTPUT=$(FILEOUT)
endif

FILE=test.hepial
ifdef FILEIN
	FILE=$(FILEIN)
endif



hepial : $(TESTS)
	@echo "============== CREATING JAVA CLASS =============="
	$(JAVA) -jar ./jasmin-2.4/jasmin.jar $(OUTPUT).j
	rm $(OUTPUT).j

all : $(FILE) sym.class parser.class $(FILE_JAVA_NAME).class $(FILE_TEST_PRG_NAME).class
	@echo "============== STARTING COMPILATION =============="
	$(JAVA) -classpath $(CLASSPATH) $(TEST_CLASS) $(FILE) $(OUTPUT)


$(FILE_JAVA_NAME).java : $(FILE_FLEX)
	$(JFLEX) $(FILE_FLEX)

sym.java parser.java : $(FILE_CUP)
	$(JAVA) -jar $(JAVACUP) $(FILE_CUP)

%.class : %.java
	$(JAVAC) -classpath $(CLASSPATH) $< 

clean :
	@echo "============== CLEANING WORKING DIRECTORY =============="
	rm -rf *class *~ parser.java sym.java $(FILE_JAVA_NAME).java



