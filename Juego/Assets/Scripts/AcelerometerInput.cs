using UnityEngine;
using System.Collections;

public class AcelerometerInput : MonoBehaviour
{
    private Quaternion localRotation; // 
    public float speed = 1.0f; // ajustable speed from Inspector in Unity editor
    private void Start()
    {
        Screen.orientation = ScreenOrientation.Landscape;

    }
    public Transform rotation;
    void Update()
    {
        Vector3 dir = Vector3.zero;
        dir.z = Input.acceleration.x;
        float eulerRotationZ = transform.rotation.eulerAngles.z;
        if (eulerRotationZ < 35 || eulerRotationZ > 325)
            transform.Rotate(0, 0, Input.acceleration.x * 1f);
    }
}
