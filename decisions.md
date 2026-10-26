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

## Persistence Framework

- We use jOOQ
- Reasons
  - jOOQ enables developers to write special queries everytime,
    forcing them to think want field really are need, and what tables really need to be `JOIN`ed
  - jOOQ and its SQL-like API is easy to use, and fast to type with auto-completion
  - jOOQ with code generation eliminate typos and provide dev-experience like auto-complete
  - No, not Hibernate! [The classic: How Hibernate almost…](https://medium.com/@ggajos/how-hibernate-almost-ruined-16f31ba7d381)
    There is no reason to introduce that complexity.
  - (jOOQ even provides paid Enterprise features, if needed)
- Future
  - In case of a switch to Kotlin, jOOQ is prepared: can generate Kotlin classes,
    Kotlin's trailing lambda will make the code even easier to read and write.

## Package Structure

- Subpackages per-layer, i.&nbsp;e. `controller`, `model`, `repository`
- Reasons
  - We just have a demo application, so the layers are easier to see
- Future
  - If the application would grow, the approach "put together what belongs together" would make more sense.
    In that case, all classes belonging to quantity reports would go in a subpackage `quantity`.
  - We see that more correct solution when comparing the number of imports
    (introduced by this commit creating the subpackages).

## Frontend Validation Framework

- We did not check in depth the many different choices.
- Nuxt UI's `Form` component works with
  > […] any validation library supporting Standard Schema such as
  > Valibot, Zod, Regle, Yup, Joi or Superstruct or your own validation logic.
- We choose Valibot
- Reasons
  - According to a ChatGPT comparision Valibot and Zod are the most TypeScript compatible ones
  - I have had bad experiences with Zod before (extensive type usage makes issues).
    Likely, a misuse, but a good reason to try something different.
- Future
  - Though the declarative syntax is nice, it add a lot of complexity for some simple `if` statements.
  - We might want to remove that lib and provide custom validation.
