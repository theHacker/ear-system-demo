# Tickets

- [ ] Analyze and understand requirements, includes
  - getting overview of provided docs
  - getting overview of provided WSDL (focused on `submitIstInput` method)
  - understanding of "Mengenmeldungen"
- Technical research (decide technologies) and initial prototype
  - [ ] Backend
  - [ ] Frontend
- Backend
  - [ ] Initial draft with Kotlin, later convert to Java to fulfill requirements
    - -> we start faster and can "go back" via VCS 
  - [ ] Binding for SOAP API
  - [ ] Prepare Persistence (-> since it's a prototype, we'll use H2)
  - Provide REST API
    - [ ] Query quantity reports
    - [ ] Add quantity report
  - [ ] Caching mechanism to allow submitting via REST API when SOAP API is temporarily down
    - Note: Requirements do not say if the submitted quantity report should be visible to the user in that case.
            We assume yes.
- Frontend
  - [ ] Initial application with a layout framework, e.g. Bootstrap
  - [ ] List view to show existing quantity reports
  - [ ] Submit form for quantity report
  - [ ] Make it nice
- [ ] Build workflow for backend and frontend (do we want to combine both for the prototype?)
- [ ] Add tests (-> since it's a prototype, we don't do TDD but add tests after an initial sketch of the code)
- [ ] Check if `README.md` is completed (instructions to build and execute)

## Additional tasks

- Prepare for presentation
  - [ ] Order VGA-to-HDMI adapter
  - [ ] Publish code to GitHub
  - [ ] Prepare code on notebook
