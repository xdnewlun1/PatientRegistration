# PatientRegistration
# About
A basic Java/MySQL project to create and store patients, insurance info, and provider info for a simple Patient Management to be used with an EMR.
Already Set to use Notes just need to add a link, or method of noting note source
Built with SHA256 Hashing on Passwords to keep Database secure, working on encryption of all data in database
Built with very basic auditing on actions.

# TODO
1. Implement Patient Managment
    a. Implement Deleting Patient
    b. Implement Adding/Removing balance via transaction
    c. Implement Changing Patient Info (Phone, Email, Ins)
2. Implement Insurance Management
    a. Implement Deleting Ins
    b. Implement Adding Ins
    c. Implement Chaning Ins info
    d. Implement GUI to add/remove providers
3. Implement Custom Server Info
    a. Use XML to store Database Connection Info
    b. Use Basic page on Login to set variables
        c. Allow Basic page to be locked with password (in XML)
4. Create Release Package with Installer
    a. Custom Release Installer to get First Time DB credentials
    b. Store admin credneitals on install that are over users
5. Create MySQL script to implement database on any database pointed to with authentication
