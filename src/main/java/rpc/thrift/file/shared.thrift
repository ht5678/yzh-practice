namespace cpp shared
namespace d share // "shared" would collide with the eponymous D keyword.
namespace java rpc.thrift.idl
namespace php shared

struct SharedStruct {
  1: i32 key
  2: string value
}

service SharedService {
  SharedStruct getStruct(1: i32 key)
}
