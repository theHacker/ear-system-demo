# Decisions

## Database

- We use H2 as a database
- Reasons
  - Allows easy in-memory tests (no mismatch test vs. production)
  - Is production ready
  - Works easy because no setup required (vs. a bigger DBMS like PostgreSQL or MariaDB)
  - Dev convenience: Database is a single file that can be copied/restored without complicated dumping/importing 
- Future
  - H2 works well for production, but does not scale well. For demo purposes it suffices.
    Later, we might move to PostgreSQL or a similar powerful database.
