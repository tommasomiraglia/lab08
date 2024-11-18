package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote {

    private Map<String, Death> deathMap= new HashMap<>();
    private static final int INVALID_CAUSE_TIME = 40;
    private static final int INVALID_DETAILS_TIME = 6000 + INVALID_CAUSE_TIME;
    private String lastNameWritten;
    private long startTime;
    /**
     * Returns the rule with the given number.
     *
     * @param ruleNumber the number of the rule to return. The first rule has number one
     * @return the rule with the given number
     * @throws IllegalArgumentException if the given rule number is smaller than 1 or larger
     * than the number of rules
     */
    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber>RULES.size()) {
            throw new IllegalArgumentException("the given rule number is smaller than 1 or larger than the number of rules");
        }
        else {
            return RULES.get(ruleNumber-1);
        }
    }

    /**
     * The human whose name is written in this DeathNote will die.
     * @param name the name of the human to kill
     * @throws NullPointerException if the given name is null.
     */
    @Override
    public void writeName(String name) {
        startTime = System.currentTimeMillis();
        if (name == null) {
            throw new NullPointerException("the given name is null");        
        }
        else {
            lastNameWritten = name;
            deathMap.put(name, new Death());
        }
    } 

    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's
     * name, it will happen.
     *
     * @param cause the cause of the human's death
     * @return true if the cause was written within 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     * or the cause is null
     */
    @Override
    public boolean writeDeathCause(String cause) {
        if (lastNameWritten == null || deathMap.isEmpty()) { 
            throw new IllegalStateException("there is no name written in this DeathNote, or the cause is null");
        }
        else {
            if (System.currentTimeMillis() - startTime <= 40) {
                deathMap.get(this.lastNameWritten).cause = cause;
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * After writing the cause of death, details of the death should be written in the next
     * 6 seconds and 40 milliseconds.
     *
     * @param details the details of the human's death
     * @return true if the details were written within 6 seconds and 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     * or the details are null
     */
    @Override
    public boolean writeDetails(String details) {
        if (lastNameWritten == null || deathMap.isEmpty()){
            throw new IllegalStateException("there is no name written in this DeathNote, or the details are null");
        }
        else {
            if (System.currentTimeMillis() - startTime <= INVALID_DETAILS_TIME) {
                deathMap.get(this.lastNameWritten).details = details;
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * Provides the cause of death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death cause of the person with the given name.
     * If the cause of death is not specified, the method will return "heart attack".
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote
     */
    @Override
    public String getDeathCause(String name) {
        if (!deathMap.containsKey(name)){
            throw new IllegalStateException("the provider name is not written in this DeathNote");
        }
        else {
            return deathMap.get(name).cause; 
            }
    }

/**
     * Provides the details of the death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death details of the person with the given name,
     * or an empty string if no details have been provided.
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    @Override
    public String getDeathDetails(String name) {
        if (!deathMap.containsKey(name)){
            throw new IllegalStateException("the provider name is not written in this DeathNote");
        }
        else { 
            return deathMap.get(name).cause; 
        }

    }


    /**
     * Checks if the given name is written in this DeathNote.
     *
     * @param name the name of the person
     * @return true if the given name is written in this DeathNote, false otherwise
     */
    @Override
    public boolean isNameWritten(String name) {
        if (deathMap.containsKey(name)) {
            return true;
        }
        else {
            return false;
        }
    }

    static class Death {
        private String cause = "";
        private String details = "heart attack";

        public Death(){
        }
        public Death(String cause){
            this.cause = cause;
        }
        public Death(String cause, String details){
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }
        public void setCause(String cause) {
            this.cause = cause;
        }
        public String getDetails() {
            return details;
        }
        public void setDetails(String details) {
            this.details = details;
        }
    }

}