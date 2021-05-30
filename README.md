# Industrial Informatics FEUP - 2020/2021

### UML Diagrams
- https://drive.google.com/drive/folders/13m_YLZfGEU5hWmuD0vRI2CjnorEUFwvu?usp=sharing

###  How to execute:
1) Configure DB with SQL queries in folder /db (user:root, pass:root)
2) Open PLC - "Codesys Control Win V3 - x64"
3) Open Codesys project (folder /plc) and Download program to PLC
4) Run Shop Floor Simulator (folder /sfs)
5) Run "run.bat" (folder /mes)
- Alternative to 5.: Open IntelliJ (with Gradle) project (folder /mes) and Run project

6) Send Orders:
- Send XML files using ncat (folder /erp/commands)
- Use ERP_GUI.jar (folder /erp/gui)
