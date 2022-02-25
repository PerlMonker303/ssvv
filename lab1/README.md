## Change log
- initial commit: (23.02.2022) 
    - changed *valNota logic* in method *saveNota* from *Service.java* to allow submission of an grade in startLine week
    - method *createFile* from the *NotaXMLRepository.java* throws an *IllegalStateException* in case a student 
                    is not found
    - method *createStudentFile* from *Service.java* catches the error mentioned above
    - comments: the XML side of things works perfectly, however the .txt file logic is not yet perfect since
    assigning a grade to a student does not store the newly introduced data in the xml files.  
