@extends('layouts.app')

    
@section('title', 'home page')
    
@section('content')
    <div>
        <h1 class="text-2xl pb-4">Hello, welcome to the land of finance</h1>
        <a class="m-1 py-2 px-3 rounded hover:shadow-lg bg-blue-300 hover:bg-blue-200 focus:bg-blue-400" href="{{route("company")}}">Companies</a>
        <a class="m-1 py-2 px-3 rounded hover:shadow-lg bg-blue-300 hover:bg-blue-200 focus:bg-blue-400" href="{{route("news.index")}}">News</a>
        @if (auth()->user()!==null && auth()->user()->difficulty ==="easy")
            <a class="m-1 py-2 px-3 rounded hover:shadow-lg bg-blue-300 hover:bg-blue-200 focus:bg-blue-400" href="{{route("profile")}}">Profile</a>
        @endif
        
    </div>
@endsection