package org.omg.IOP;


/**
* org/omg/IOP/TaggedComponentHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../src/share/classes/org/omg/PortableInterceptor/IOP.idl
* Monday, November 10, 2008 6:32:14 AM GMT
*/


/**
     * <code>TaggedComponents</code> contained in 
     * <code>TAG_INTERNET_IOP</code> and 
     * <code>TAG_MULTIPLE_COMPONENTS</code> profiles are identified by 
     * unique numeric tags using a namespace distinct form that is used for 
     * profile tags. Component tags are assigned by the OMG.
     * <p>
     * Specifications of components must include the following information:
     * <ul>
     *   <li><i>Component ID</i>: The compound tag that is obtained 
     *       from OMG.</li>
     *   <li><i>Structure and encoding</i>: The syntax of the component 
     *       data and the encoding rules.  If the component value is 
     *       encoded as a CDR encapsulation, the IDL type that is
     *       encapsulated and the GIOP version which is used for encoding 
     *       the value, if different than GIOP 1.0, must be specified as 
     *       part of the component definition.</li>
     *   <li><i>Semantics</i>: How the component data is intended to be 
     *       used.</li>
     *   <li><i>Protocols</i>: The protocol for which the component is 
     *       defined, and whether it is intended that the component be 
     *       usable by other protocols.</li>
     *   <li><i>At most once</i>: whether more than one instance of this 
     *       component can be included in a profile.</li>
     * </ul>
     * Specification of protocols must describe how the components affect 
     * the protocol. The following should be specified in any protocol 
     * definition for each <code>TaggedComponent</code> that the protocol uses:
     * <ul>
     *   <li><i>Mandatory presence</i>: Whether inclusion of the component 
     *       in profiles supporting the protocol is required (MANDATORY 
     *       PRESENCE) or not required (OPTIONAL PRESENCE).</li>
     *   <li><i>Droppable</i>: For optional presence component, whether 
     *       component, if present, must be retained or may be dropped.</li>
     * </ul>
     */
abstract public class TaggedComponentHelper
{
  private static String  _id = "IDL:omg.org/IOP/TaggedComponent:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.IOP.TaggedComponent that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.IOP.TaggedComponent extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.IOP.ComponentIdHelper.id (), "ComponentId", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "tag",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_octet);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "component_data",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.omg.IOP.TaggedComponentHelper.id (), "TaggedComponent", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.IOP.TaggedComponent read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.IOP.TaggedComponent value = new org.omg.IOP.TaggedComponent ();
    value.tag = istream.read_ulong ();
    int _len0 = istream.read_long ();
    value.component_data = new byte[_len0];
    istream.read_octet_array (value.component_data, 0, _len0);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.IOP.TaggedComponent value)
  {
    ostream.write_ulong (value.tag);
    ostream.write_long (value.component_data.length);
    ostream.write_octet_array (value.component_data, 0, value.component_data.length);
  }

}
