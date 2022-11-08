using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

public class ClassWithCyclicDependency : Class {
    public ClassWithCyclicDependency Child { get; set; }
}