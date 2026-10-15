package com.switchfully.digibookygroup3.domain.book;

import java.util.UUID;

public class Book {
        private final String id;
        private final String isbn;
        private final String title;
        private final String authorId;
        private boolean isAvailable;
        private boolean isDeleted;
        private String summary;

        public Book(String isbn, String title, String authorId, String summary) {
                this.id = UUID.randomUUID().toString();
                this.isbn = isbn;
                this.title = title;
                this.authorId = authorId;
                this.isAvailable = true;
                this.isDeleted = false;
                this.summary = summary;
        }

        public void softDelete() {
                isDeleted = true;
        }

        public void restoreBook() {
                isDeleted = false;
        }

        public String getId() {
                return id;
        }

        public String getIsbn() {
                return isbn;
        }

        public String getTitle() {
                return title;
        }

        public String getAuthorId() {
                return authorId;
        }

        public String getSummary() {
                return summary;
        }

        public boolean isDeleted() {
                return isDeleted;
        }
}
