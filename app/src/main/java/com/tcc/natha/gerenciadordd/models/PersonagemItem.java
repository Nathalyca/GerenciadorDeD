package com.tcc.natha.gerenciadordd.models;

/**
 * Created by natha on 22/07/2017.
 */

public class PersonagemItem {
        public final String id;
        public final String content;
        public final String details;

        public PersonagemItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }

}
