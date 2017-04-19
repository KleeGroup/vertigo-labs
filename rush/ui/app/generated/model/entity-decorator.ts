
import "reflect-metadata";

const DomainMetadataKey = Symbol("Domain");
const RequiredMetadataKey = Symbol("Required");
const LabelMetadataKey = Symbol("Label");
const TypeMetadataKey = Symbol("Type");

export function Domain(domainName: string) {
    return Reflect.metadata(DomainMetadataKey, domainName);
}

export function getDomain(target: any, propertyKey: string) :string {
    return Reflect.getMetadata(DomainMetadataKey, target, propertyKey);
}

export function Required(requiredValue: boolean) {
    return Reflect.metadata(RequiredMetadataKey, requiredValue);
}

export function getRequired(target: any, propertyKey: string) : boolean {
    return Reflect.getMetadata(RequiredMetadataKey, target, propertyKey);
}

export function Label(domainName: string) {
    return Reflect.metadata(LabelMetadataKey, domainName);
}

export function getLabel(target: any, propertyKey: string) :string {
    return Reflect.getMetadata(LabelMetadataKey, target, propertyKey);
}

export function Type(domainName: string) {
    return Reflect.metadata(TypeMetadataKey, domainName);
}

export function getType(target: any, propertyKey: string) :string {
    return Reflect.getMetadata(TypeMetadataKey, target, propertyKey);
}

