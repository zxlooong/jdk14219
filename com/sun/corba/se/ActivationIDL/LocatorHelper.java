package com.sun.corba.se.ActivationIDL;


/**
* com/sun/corba/se/ActivationIDL/LocatorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../../../src/share/classes/com/sun/corba/se/ActivationIDL/activation.idl
* Sunday, November 9, 2008 11:00:02 PM PST
*/

abstract public class LocatorHelper
{
  private static String  _id = "IDL:ActivationIDL/Locator:1.0";

  public static void insert (org.omg.CORBA.Any a, com.sun.corba.se.ActivationIDL.Locator that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.sun.corba.se.ActivationIDL.Locator extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.sun.corba.se.ActivationIDL.LocatorHelper.id (), "Locator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.sun.corba.se.ActivationIDL.Locator read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LocatorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.sun.corba.se.ActivationIDL.Locator value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.sun.corba.se.ActivationIDL.Locator narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.sun.corba.se.ActivationIDL.Locator)
      return (com.sun.corba.se.ActivationIDL.Locator)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.sun.corba.se.ActivationIDL._LocatorStub stub = new com.sun.corba.se.ActivationIDL._LocatorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
