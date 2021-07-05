@extends('layouts.app')

    
@section('title', 'News')
    
@section('content')
    <form  method="POST" action = "{{ route('news.search')}}">

        @csrf

        <label for="cSymbol" >Enter company Symbol:  </label>
        <input type="text" id="cSymbol" class=" shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent"  placeholder="e.g MSFT" name="companySymbol">
        <label for = "start_date" >start date:   </label>
        <input type="date" id ="start_data"  class=" shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" name = "startDate" min ="2015-01-01" max ="{{date('Y-m-d')}}">
        <label for ="finish_date">finish date:  </label>
        <input type="date" id="finish_date" class=" shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" name = "finishDate" min ="2015-01-01" max ="{{date('Y-m-d')}}">
        <input type="submit" class="py-2 px-4 rounded text-white font-semibold tracking-wider shadow-lg bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-4 focus:ring-blue-600 focus:ring-opacity-50" value="Search" >
    </form>
    <br/>
    @if ($data ===[])
        <h1>No records found</h1>
    @endif
    <div class="grid lg:grid-cols-3 gap-0 md:grid-cols-2 xl:grid-cols-4" >
        @foreach ($data as $article)
            @include('news._news_articles')
    @endforeach
    </div>
    
@endsection