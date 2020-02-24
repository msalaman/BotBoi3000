BotBoi3000: TestBot1.class
	jar cvfe BotBoi3000.jar manifest.mf -C ./ .

TestBot1.class: TestBot1.java
	javac -cp "../lib/bwmirror_v2_5.jar" *.java ./behaviorTree/*.java ./blackboard/*.java ./node/*.java ./routine/*.java

clean:
	rm *.class
	