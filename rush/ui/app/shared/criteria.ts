export class Criteria {

    public term: string;
    constructor() {

    }

    static fromQueryParams(params: any) {
        var newCriteria = new Criteria();
        newCriteria.term = params['q'];
        return newCriteria
    }

    toQueryParams() {
        return { 'q': this.term }
    }

}

export class ListState {

    public sortFieldName: string;
    public sortDesc: boolean;
    public skipRows: number;
    public maxRows: number;
    constructor(pageSize?: number) {
        this.skipRows = 0
        this.maxRows = pageSize || 50;
    }

    static fromQueryParams(params: any) {
        var newListState = new ListState();
        newListState.sortFieldName = params['listState.sortFieldName'];
        newListState.sortDesc = params['listState.sortDesc'];
        newListState.skipRows = params['listState.skipRows'];
        newListState.maxRows = params['listState.maxRows'];
        return newListState
    }

    toQueryParams() {
        return {
            'listState.sortFieldName': this.sortFieldName,
            'listState.sortDesc': this.sortDesc,
            'listState.skipRows': this.skipRows,
            'listState.maxRows': this.maxRows,
        }
    }

}


export class Facets {
    static fromQueryParams(params: any) {
        var facets = new Facets();
        Object.keys(params)
            .filter(key => key.startsWith('facet.'))
            .forEach(key => {
                facets[key.substring(6, key.length)] = params[key];
            })
        return facets;

    }

    toQueryParams() {
        var params = new Object();
        Object.keys(this).forEach(key =>
            params['facet.' + key] = this[key]
        )
        return params;
    }

    hasValue(facetCode: string) {
        return this[facetCode] != undefined;
    }

}

export class FacetedQueryResult<T> {
    constructor(public list?: T[], public facets?: Facet[], public totalCount?: number) {
        this.list = list ? list : [];
        this.facets = facets ? facets : [];
        this.totalCount = totalCount ? totalCount : null;
    }
}

export class Facet {
    constructor(public code: string, public facetValues: FacetValue[]) { }
}

export class FacetValue {
    constructor(public value: string, public count: number) { }
}