package com.sun.corba.se.ActivationIDL;


/**
* com/sun/corba/se/ActivationIDL/RepositoryOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../../../src/share/classes/com/sun/corba/se/ActivationIDL/activation.idl
* Sunday, November 9, 2008 11:00:02 PM PST
*/

public interface RepositoryOperations 
{

  // always uninstalled.
  int registerServer (com.sun.corba.se.ActivationIDL.RepositoryPackage.ServerDef serverDef) throws com.sun.corba.se.ActivationIDL.ServerAlreadyRegistered, com.sun.corba.se.ActivationIDL.BadServerDefinition;

  // unregister server definition
  void unregisterServer (int serverId) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered;

  // get server definition
  com.sun.corba.se.ActivationIDL.RepositoryPackage.ServerDef getServer (int serverId) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered;

  // Return whether the server has been installed
  boolean isInstalled (int serverId) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered;

  // if the server is currently marked as installed.
  void install (int serverId) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered, com.sun.corba.se.ActivationIDL.ServerAlreadyInstalled;

  // if the server is currently marked as uninstalled.
  void uninstall (int serverId) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered, com.sun.corba.se.ActivationIDL.ServerAlreadyUninstalled;

  // list registered servers
  int[] listRegisteredServers ();

  // servers.
  String[] getApplicationNames ();

  // Find the ServerID associated with the given application name.
  int getServerID (String applicationName) throws com.sun.corba.se.ActivationIDL.ServerNotRegistered;
} // interface RepositoryOperations
