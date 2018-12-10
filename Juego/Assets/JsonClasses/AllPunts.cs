using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Assets.JsonClasses
{
    class AllPunts
    {
        public List<SinglePunt> allPunt;

        public AllPunts(List<SinglePunt> all)
        {
            allPunt = all;
        }
    }
}
