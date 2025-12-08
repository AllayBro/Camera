
package org.example.camera.wsdl;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.camera.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.camera.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Photo }
     * 
     * @return
     *     the new instance of {@link Photo }
     */
    public Photo createPhoto() {
        return new Photo();
    }

    /**
     * Create an instance of {@link DroneTarget }
     * 
     * @return
     *     the new instance of {@link DroneTarget }
     */
    public DroneTarget createDroneTarget() {
        return new DroneTarget();
    }

    /**
     * Create an instance of {@link DroneTarget.Target }
     * 
     * @return
     *     the new instance of {@link DroneTarget.Target }
     */
    public DroneTarget.Target createDroneTargetTarget() {
        return new DroneTarget.Target();
    }

    /**
     * Create an instance of {@link DroneTarget.Drone }
     * 
     * @return
     *     the new instance of {@link DroneTarget.Drone }
     */
    public DroneTarget.Drone createDroneTargetDrone() {
        return new DroneTarget.Drone();
    }

    /**
     * Create an instance of {@link GetDroneInfoRequest }
     * 
     * @return
     *     the new instance of {@link GetDroneInfoRequest }
     */
    public GetDroneInfoRequest createGetDroneInfoRequest() {
        return new GetDroneInfoRequest();
    }

    /**
     * Create an instance of {@link GetDroneInfoResponse }
     * 
     * @return
     *     the new instance of {@link GetDroneInfoResponse }
     */
    public GetDroneInfoResponse createGetDroneInfoResponse() {
        return new GetDroneInfoResponse();
    }

    /**
     * Create an instance of {@link RegisterFineRequest }
     * 
     * @return
     *     the new instance of {@link RegisterFineRequest }
     */
    public RegisterFineRequest createRegisterFineRequest() {
        return new RegisterFineRequest();
    }

    /**
     * Create an instance of {@link RegisterFineResponse }
     * 
     * @return
     *     the new instance of {@link RegisterFineResponse }
     */
    public RegisterFineResponse createRegisterFineResponse() {
        return new RegisterFineResponse();
    }

    /**
     * Create an instance of {@link GetFinesRequest }
     * 
     * @return
     *     the new instance of {@link GetFinesRequest }
     */
    public GetFinesRequest createGetFinesRequest() {
        return new GetFinesRequest();
    }

    /**
     * Create an instance of {@link GetFinesResponse }
     * 
     * @return
     *     the new instance of {@link GetFinesResponse }
     */
    public GetFinesResponse createGetFinesResponse() {
        return new GetFinesResponse();
    }

    /**
     * Create an instance of {@link InvalidDroneIdFault }
     * 
     * @return
     *     the new instance of {@link InvalidDroneIdFault }
     */
    public InvalidDroneIdFault createInvalidDroneIdFault() {
        return new InvalidDroneIdFault();
    }

    /**
     * Create an instance of {@link ServiceUnavailableFault }
     * 
     * @return
     *     the new instance of {@link ServiceUnavailableFault }
     */
    public ServiceUnavailableFault createServiceUnavailableFault() {
        return new ServiceUnavailableFault();
    }

    /**
     * Create an instance of {@link ValidationFault }
     * 
     * @return
     *     the new instance of {@link ValidationFault }
     */
    public ValidationFault createValidationFault() {
        return new ValidationFault();
    }

    /**
     * Create an instance of {@link Photo.Coordinates }
     * 
     * @return
     *     the new instance of {@link Photo.Coordinates }
     */
    public Photo.Coordinates createPhotoCoordinates() {
        return new Photo.Coordinates();
    }

    /**
     * Create an instance of {@link Photo.PhotoMetadata }
     * 
     * @return
     *     the new instance of {@link Photo.PhotoMetadata }
     */
    public Photo.PhotoMetadata createPhotoPhotoMetadata() {
        return new Photo.PhotoMetadata();
    }

    /**
     * Create an instance of {@link DroneTarget.PhotoDatabase }
     * 
     * @return
     *     the new instance of {@link DroneTarget.PhotoDatabase }
     */
    public DroneTarget.PhotoDatabase createDroneTargetPhotoDatabase() {
        return new DroneTarget.PhotoDatabase();
    }

    /**
     * Create an instance of {@link DroneTarget.Target.Coordinates }
     * 
     * @return
     *     the new instance of {@link DroneTarget.Target.Coordinates }
     */
    public DroneTarget.Target.Coordinates createDroneTargetTargetCoordinates() {
        return new DroneTarget.Target.Coordinates();
    }

    /**
     * Create an instance of {@link DroneTarget.Target.Photos }
     * 
     * @return
     *     the new instance of {@link DroneTarget.Target.Photos }
     */
    public DroneTarget.Target.Photos createDroneTargetTargetPhotos() {
        return new DroneTarget.Target.Photos();
    }

    /**
     * Create an instance of {@link DroneTarget.Drone.Photos }
     * 
     * @return
     *     the new instance of {@link DroneTarget.Drone.Photos }
     */
    public DroneTarget.Drone.Photos createDroneTargetDronePhotos() {
        return new DroneTarget.Drone.Photos();
    }

}
