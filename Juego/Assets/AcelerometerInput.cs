using UnityEngine;
using System.Collections;

public class AcelerometerInput : MonoBehaviour
{
    public Transform rotation;
    void Update()
    {
       transform.Rotate(Input.acceleration.x * 0.1f, 0, 0);
    }
}
