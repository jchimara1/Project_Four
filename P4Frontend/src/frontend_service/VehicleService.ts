import {Vehicle} from "../types/Vehicle.ts";
import axios, {AxiosResponse} from "axios";


// type Create = () => Promise<Vehicle>;

export const create = (vehicle: Omit<Vehicle, "id">) => (
    axios.post('/api/vehicle', vehicle)
        .then((r: AxiosResponse<Vehicle>)=> r.data)
);